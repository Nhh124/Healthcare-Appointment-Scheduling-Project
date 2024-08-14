package com.fx21314.asm3.service;

import com.fx21314.asm3.dto.ReqRes;

import com.fx21314.asm3.dto.saveCode;
import com.fx21314.asm3.entity.User;
import com.fx21314.asm3.repository.RoleRepo;
import com.fx21314.asm3.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    saveCode saveVerifyCode = new saveCode();

    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signup(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            // Kiểm tra mật khẩu và mật khẩu xác nhận
            if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
                resp.setStatusCode(400); // Bad request
                resp.setError("Passwords do not match");
                return resp;
            }

            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(roleRepo.findByName(registrationRequest.getRole()));
            user.setName(registrationRequest.getName());
            user.setStatus(1);
            User userResult = userRepo.save(user);

            if (userResult != null && userResult.getId() > 0) {
                resp.setOurUsers(userResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(ReqRes signInRequest) {
        ReqRes response = new ReqRes();
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
            var user = userRepo.findByEmail(signInRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: " + user);
            if (user.getStatus() != 1) {
                response.setStatusCode(401);
                response.setError("Account is not active. Please contact administrator.");
            } else {
                String jwt = jwtUtils.generateToken(user);
                String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshToken);
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Signed In");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequired){
        ReqRes response = new ReqRes();
        String email = jwtUtils.extractUserName(refreshTokenRequired.getToken());
        User user = userRepo.findByEmail(email).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequired.getToken(),user)){
            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequired.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed In");
        }
        response.setStatusCode(500);
        return  response;
    }

    public ReqRes forgotPassword(ReqRes forgotPasswordRequest) {
        ReqRes resp = new ReqRes();

        try {
            // 1. Sinh mã xác thực ngẫu nhiên (ví dụ: mã bảo mật 6 chữ số)
            String verificationCode = generateRandomVerificationCode();

            // 2. Lưu mã xác thực vào đối tượng ReqRes
            resp.setVerificationCode(verificationCode);

            // 3. Gửi email chứa mã xác thực tới địa chỉ email đã đăng ký
            sendVerificationEmail(forgotPasswordRequest.getEmail(), verificationCode);

            saveVerifyCode.setVerifyCode(verificationCode);

            resp.setStatusCode(200);
            resp.setMessage("Please check your email for verification code");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    // Hàm sinh mã xác thực ngẫu nhiên
    private String generateRandomVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        // Sinh ngẫu nhiên 6 chữ số và thêm vào StringBuilder
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
            code.append(digit);
        }

        return code.toString();
    }

    // Hàm gửi email chứa mã xác thực
    private void sendVerificationEmail(String email, String verificationCode) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("hai.nhtest0901@gmail.com");
            message.setTo(email);
            message.setSubject("Verification Code for Password Reset");
            message.setText("Your verification code is: " + verificationCode);
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public ReqRes processResetPasswordRequest(ReqRes resetPasswordRequest) {
        ReqRes resp = new ReqRes();

        try {
            String newPassword = resetPasswordRequest.getNewPassword();
            String confirmPassword = resetPasswordRequest.getConfirmPassword();
            String enteredVerificationCode = resetPasswordRequest.getVerifyCode(); // Lấy mã xác thực từ người dùng

            if (!newPassword.equals(confirmPassword)) {
                resp.setStatusCode(400);
                resp.setMessage("Passwords do not match. Please try again.");
                return resp;
            }

            // Kiểm tra mã xác thực

            if (!saveVerifyCode.getVerifyCode().equals(enteredVerificationCode)) {
                resp.setStatusCode(400);
                resp.setMessage("Invalid verification code. Please enter the correct code.");
                return resp;
            }

            // Xác thực email và mã xác thực, sau đó reset mật khẩu
            if (emailVerifiedSuccessfully(resetPasswordRequest.getEmail())) {
                User user = userRepo.findByEmail(resetPasswordRequest.getEmail()).orElseThrow();

                user.setPassword(passwordEncoder.encode(newPassword));

                userRepo.save(user);

                resp.setStatusCode(200);
                resp.setMessage("Password reset successfully");
            } else {
                resp.setStatusCode(400);
                resp.setMessage("Email verification failed. Please request a new verification code.");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }

        return resp;
    }

    private boolean emailVerifiedSuccessfully(String email) {
        var user = userRepo.findByEmail(email);
        return user.isPresent(); // Trả về true nếu user tồn tại, ngược lại trả về false
    }

}
