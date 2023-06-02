package com.ozank.cpathway.simulation;

import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Set;

public class SimulationTest {
    @Test
    public void setTest(){
        Set<String> set1 = Set.of("R-HSA-5205670","R-HSA-5205671","R-HSA-1252240","R-HSA-201579","R-HSA-5205679",
                "R-HSA-113595","R-HSA-5205659 ","R-HSA-992714","R-HSA-193937","R-HSA-5205671 ","R-HSA-5205637",
                "R-HSA-5205638","R-HSA-992745","R-HSA-5205630","R-HSA-5205668","R-HSA-1267988","R-HSA-5205624",
                "R-HSA-5205653","R-HSA-5205658","R-HSA-5205659","R-HSA-5205683","R-HSA-5205688");
        System.out.println(set1.size());
    }
}
