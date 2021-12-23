package com.hell.command;

import com.hell.core.command.Command;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验
 */
@Component("RoleControlCommand")
public class RoleControlCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, Object o) throws Exception {

    }
}
