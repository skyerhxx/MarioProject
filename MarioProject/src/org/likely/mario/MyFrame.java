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

public class MyFrame extends JFrame implements KeyListener,Runnable {

    private List<BackGround> allBG = new ArrayList<BackGround>();

    private Mario mario = null;

    private BackGround nowBG = null;

    private Thread t = new Thread(this);

    //是否已经开始游戏
    private boolean isStart = false;

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
        this.setResizable(false);

        //初始化全部的图片
        StaticValue.init();

        //创建全部的场景
        for(int i=1;i<=3;i++){
            this.allBG.add(new BackGround(i,i==3?true:false));
        }
        //将第一个场景设置为当前场景
        this.nowBG = this.allBG.get(0);
        //初始化Mario对象
        this.mario = new Mario(0,480);
        //将场景放入mario对象的属性中
        this.mario.setBg(nowBG);

        this.repaint();

        this.addKeyListener(this);

        t.start();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口的同时把程序停止
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        //TODO Auto-generated method stub
        //建立临时的缓冲图片
        BufferedImage image = new BufferedImage(900,600,BufferedImage.TYPE_3BYTE_BGR);
        Graphics g2 = image.getGraphics();

        if(this.isStart) {

            //绘制背景
            g2.drawImage(this.nowBG.getBgImage(), 0, 0, this);

            g2.drawString("生命: "+ this.mario.getLife(), 700, 100);

            //绘制敌人
            Iterator<Enemy> iterEnemy = this.nowBG.getAllEnemy().iterator();
            //遍历画出敌人，如果有下一个
            while (iterEnemy.hasNext()) {
                Enemy e = iterEnemy.next();
                g2.drawImage(e.getShowImage(), e.getX(), e.getY(), this);
            }

            //绘制障碍物
            Iterator<Obstruction> iter = this.nowBG.getAllObstruction().iterator();
            while (iter.hasNext()) {
                Obstruction ob = iter.next();
                g2.drawImage(ob.getShowImage(), ob.getX(), ob.getY(), this);
            }
            // 添加Mario
            g2.drawImage(this.mario.getShowImage(), this.mario.getX(), this.mario.getY(), this);

        }
        else
        {
            g2.drawImage(StaticValue.startImage,0,0,this);
        }
        //把缓冲图片绘制到窗体中
        g.drawImage(image,0,0,this);
    }


    //当点击键盘上某一个键时
    public void keyPressed(KeyEvent ke){
        if(this.isStart) {
            //System.out.println(ke.getKeyCode());
            //当按下39时(-->),mario向右移动
            if (ke.getKeyCode() == 39) {
                this.mario.rightMove();
            }
            //当按下37时(<--),mario向右移动
            if (ke.getKeyCode() == 37) {
                this.mario.leftMove();
            }
            //当按下32时(空格),mario开始跳跃
            if (ke.getKeyCode() == 32) {
                this.mario.jump();
            }
        }else{
            if(ke.getKeyCode() == 32){
                this.isStart = true;
                this.nowBG.enemyStartMove();
                this.mario.setScore(0);
                this.mario.setLife(3);
            }
        }
    }

    //当抬起键盘上某一个键时
    public void keyReleased(KeyEvent ke){
        if(this.isStart) {
            //当抬起39时(-->),mario停止向右移动
            if (ke.getKeyCode() == 39) {
                this.mario.rightStop();
            }
            //当抬起37时(<--),mario停止向右移动
            if (ke.getKeyCode() == 37) {
                this.mario.leftStop();
            }
        }
    }

    //当通过键盘输入一些信息时
    public void keyTyped(KeyEvent arg0){
        //TODO Auto-generated method stub

    }

    @Override
    public void run() {
        while(true){
            this.repaint();
            try {
                Thread.sleep(50);  //刷新的频率要与Mario相同
                if(this.mario.getX() >= 840){
                    //切换场景
                    this.nowBG = this.allBG.get(this.nowBG.getSort());
                    //将当前场景设置mario中
                    this.mario.setBg(this.nowBG);
                    //将场景中的敌人移动
                    this.nowBG.enemyStartMove();

                    //修改mario的坐标
                    this.mario.setX(0);  //从最右边挪到最左边
                }
                if(this.mario.isDead()){
                    JOptionPane.showMessageDialog(this, "游戏结束,Mario死亡！");
                    System.exit(0);
                }
                if(this.mario.isClear()){
                    JOptionPane.showMessageDialog(this, "恭喜您,游戏通关,请期待以后的关卡！");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
