package org.likely.mario;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticValue {
    //全部马里奥的图片
    public static List<BufferedImage> allMarioImage = new ArrayList<BufferedImage>();
    //背景的图片
    public static BufferedImage startImage = null;
    public static BufferedImage endImage = null;
    public static BufferedImage bgImage = null;
    //食人花的图片
    public static List<BufferedImage> allFlowerImage = new ArrayList<BufferedImage>();
    //敌人的图片
    public static List<BufferedImage> allTriangleImage = new ArrayList<BufferedImage>();
    //乌龟的图片
    public static List<BufferedImage> allTurtleImage = new ArrayList<BufferedImage>();
    //障碍物的图片
    public static List<BufferedImage> allObstructionImage = new ArrayList<BufferedImage>();
    //马里奥死亡的图片
    public static BufferedImage marioDeadImage = null;


    public static String imagePath=System.getProperty("user.dir")+"/src/resources/";


    //将所有图片初始化，将所有图片导入到系统中
    public static void init(){
        //将所有马里奥的图片保存到静态属性中
        for(int i=1;i<=10;i++){
            try {
                //allMarioImage.add(ImageIO.read(new File(System.getProperty("user.dir") + "/src/resources/" + i + ".gif")));
                allMarioImage.add(ImageIO.read(new File(imagePath+i+".gif")));
            }catch (IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //导入全部背景图片
        try {
            startImage=ImageIO.read(new File(imagePath+"start.gif"));
            endImage=ImageIO.read(new File(imagePath+"firststageend.gif"));
            bgImage=ImageIO.read(new File(imagePath+"firststage.gif"));
            marioDeadImage=ImageIO.read(new File(imagePath+"over.gif"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //导入全部敌人图片
        for(int i=1;i<=5;i++){
            try {
                if(i<=2){
                    allFlowerImage.add(ImageIO.read(new File(imagePath+"flower"+i+".gif")));
                }

                if(i<=3){
                    allTriangleImage.add(ImageIO.read(new File(imagePath+"triangle"+i+".gif")));
                }

                allTurtleImage.add(ImageIO.read(new File(imagePath+"Turtle"+i+".gif")));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //导入障碍物图片
        for(int i=1;i<=12;i++){
            try {
                allObstructionImage.add(ImageIO.read(new File(imagePath+"ob"+i+".gif")));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        //导入mario死亡的图片
        try {
            marioDeadImage = ImageIO.read(new File(imagePath + "over.gif"));
        }catch (IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
