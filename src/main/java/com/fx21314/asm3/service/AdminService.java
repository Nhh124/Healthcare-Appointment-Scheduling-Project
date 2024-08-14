package com.fx21314.asm3.service;

import com.fx21314.asm3.dto.ReqRes;
import com.fx21314.asm3.entity.User;
import com.fx21314.asm3.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    UserRepo userRepo;

    public ReqRes lockAccount(ReqRes lockAccountRequest){
        ReqRes resp = new ReqRes();

        try {
            User user = userRepo.findByEmail(lockAccountRequest.getEmail()).orElseThrow();
            if (!user.getRole().getName().equals("admin") && user.getStatus()==1){
                user.setStatus(0);
                user.setReason(lockAccountRequest.getReason());
                userRepo.save(user);

                resp.setStatusCode(200);
                resp.setOurUsers(user);
                resp.setMessage("User Locked Or Denied Successfully");

            } else {
                resp.setStatusCode(400);
                resp.setMessage("User Locked Fail!");
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes unlockAccount(ReqRes unlockAccountRequest){
        ReqRes resp = new ReqRes();

        try {
            User user = userRepo.findByEmail(unlockAccountRequest.getEmail()).orElseThrow();
            if (!user.getRole().getName().equals("admin") && user.getStatus() == 0){
                user.setStatus(1);
                user.setReason(null);
                userRepo.save(user);

                resp.setStatusCode(200);
                resp.setOurUsers(user);
                resp.setMessage("User Unlocked Successfully");

            } else {
                resp.setStatusCode(400);
                resp.setMessage("User Unlocked Fail!");
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }




}
