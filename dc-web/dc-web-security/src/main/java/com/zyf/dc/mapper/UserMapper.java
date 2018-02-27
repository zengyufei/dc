package com.zyf.dc.mapper;

import com.zyf.dc.entity.sys.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @version V1.0.0
 */
@Mapper
@Component
public interface UserMapper {


    User getUserFromDatabase(@Param("username") String username);

}
