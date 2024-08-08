package com.project.demo.Services;

import com.project.demo.model.Admin;

import java.util.List;

public interface AdminService {
    Admin createAdmin(Admin admin);
    List<Admin> getAllAdmins();
    Admin updateAdmin(Long id, Admin admin);
    void deleteAdmin(Long id);
}
