package com.fx21314.asm3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fx21314.asm3.entity.DoctorUser;
import com.fx21314.asm3.entity.User;
import lombok.Data;

/**
 * Đây là một lớp DTO (Data Transfer Object) để chứa thông tin liên quan đến request và response trong ứng dụng.
 * Các trường trong lớp này được sử dụng để đại diện cho thông tin như mã trạng thái, thông báo lỗi, token, thông điệp, và thông tin người dùng.
 *
 * Các trường trong lớp ReqRes:
 * - statusCode: Được sử dụng để chứa mã trạng thái của request hoặc response.
 * - error: Chứa thông tin về lỗi nếu có khi xử lý request hoặc response.
 * - message: Chứa thông điệp hoặc thông tin cần trả về từ request hoặc response.
 * - token: Dùng để lưu trữ token cho xác thực.
 * - refreshToken: Lưu trữ token để làm mới (refresh) token.
 * - expirationTime: Thời gian hết hạn của token.
 * - name: Tên người dùng.
 * - email: Địa chỉ email của người dùng.
 * - password: Mật khẩu của người dùng.
 * - role: Vai trò (quyền hạn) của người dùng.
 * - confirmPassword: Xác nhận mật khẩu.
 * - verificationCode: Mã xác nhận.
 * - verifyCode: Mã xác nhận khác.
 * - ourUsers: Đối tượng User, có thể là đối tượng của một người dùng trong hệ thống.
 * - newPassword: Mật khẩu mới.
 *
 * Lớp này được sử dụng để truyền dữ liệu giữa client và server thông qua các request và response trong ứng dụng.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String address;
    private String phone;
    private String avatar;
    private String email;
    private String password;
    private String role;
    private String confirmPassword;
    private String verificationCode;
    private String verifyCode;
    private User ourUsers;
    private String gender;
    private String reason;
    private String newPassword;
    private DoctorDTO doctor;
}
