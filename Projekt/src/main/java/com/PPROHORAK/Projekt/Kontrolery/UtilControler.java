package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.PlatformyDao;
import com.PPROHORAK.Projekt.Model.Platforma;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPlatforem;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Controller
@Data
public class UtilControler {



    public  void GetPlatformList(Map<String, Object> model,PlatformyDao seznamPlatformy){

        SeznamPlatforem platformy = new SeznamPlatforem();
        platformy.getSeznamPlatformy().addAll(seznamPlatformy.findAll());

        model.put("ListPlatformy", platformy.getSeznamPlatformy());





    }


}
