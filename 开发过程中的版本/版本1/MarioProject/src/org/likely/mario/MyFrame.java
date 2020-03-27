package org.likely.mario;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyFrame extends JFrame implements KeyListener {

    private List<BackGround> allBG = new ArrayList<BackGround>();

    private BackGround nowBG = null;

    public static void main(String[] args){
        new MyFrame();
    }

    //窗体
    public MyFrame(){
        this.setTitle("马里奥游戏程序");
        this.setSize(900,600);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        //取得了屏幕的宽度和高度就可以根据900与600的整个程序的宽度，计算出当前要显示的位置，把显示的窗体显示到正中央
        this.setLocation((width-900)/2, (height-600)/2);
        this.setResizable(false);//

        //初始化全部的图片
        StaticValue.init();

        //创建全部的场景
        for(int i=1;i<=3;i++){
            this.allBG.add(new BackGround(i,i==3?true:false));
        }
        //将第一个场景设置为当前场景
        this.nowBG = this.allBG.get(0);

        this.repaint();

        this.addKeyListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口的同时把程序停止
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        //TODO Auto-generated method stub
        //建立临时的缓冲图片
        BufferedImage image = new BufferedImage(900,600,BufferedImage.TYPE_3BYTE_BGR);
        Graphics g2 = image.getGraphics();
        g2.drawImage(this.nowBG.getBgImage(),0,0,this);

        //把缓冲图片绘制到窗体中
        g.drawImage(image,0,0,this);
    }


    //当点击键盘上某一个键时
    public void keyPressed(KeyEvent arg0){
        //TODO Auto-generated method stub

        System.out.println(arg0.getKeyCode());
    }

    //当抬起键盘上某一个键时
    public void keyReleased(KeyEvent arg0){
        //TODO Auto-generated method stub

    }

    //当通过键盘输入一些信息时
    public void keyTyped(KeyEvent arg0){
        //TODO Auto-generated method stub

    }
}
