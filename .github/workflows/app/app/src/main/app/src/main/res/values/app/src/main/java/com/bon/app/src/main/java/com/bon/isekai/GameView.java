package com.bon.isekai;

import android.content.*; import android.graphics.*; import android.view.*; import java.util.*;

public class GameView extends View {
 Paint p=new Paint(); Random r=new Random(4); float px=500,py=330; float joyX=120,joyY=620; boolean joy=false; long last=System.currentTimeMillis();
 ArrayList<Enemy> enemies=new ArrayList<>(); float flash=0; int level=1; long xp=0; String msg="CHÀO MỪNG ĐẾN ISEKAI";
 public GameView(Context c){super(c); p.setAntiAlias(false); for(int i=0;i<12;i++) enemies.add(new Enemy(250+r.nextInt(800),150+r.nextInt(430)));}
 void txt(Canvas c,String s,float x,float y,float size){p.setTextSize(size);p.setColor(Color.WHITE);p.setTypeface(Typeface.MONOSPACE);c.drawText(s,x,y,p);}
 protected void onDraw(Canvas c){int w=getWidth(),h=getHeight(); p.setColor(Color.rgb(25,65,42));c.drawRect(0,0,w,h,p);
  // pixel grass
  p.setColor(Color.rgb(35,82,48)); for(int x=0;x<w;x+=32)for(int y=0;y<h;y+=32)c.drawRect(x+8,y+20,x+12,y+24,p);
  // river
  p.setColor(Color.rgb(30,100,145)); c.drawRect(0,80,w,145,p);
  for(Enemy e:enemies){if(e.alive){p.setColor(Color.rgb(150,55,75));c.drawRect(e.x-14,e.y-14,e.x+14,e.y+14,p);p.setColor(Color.BLACK);c.drawRect(e.x-8,e.y-8,e.x-3,e.y-3,p);c.drawRect(e.x+3,e.y-8,e.x+8,e.y-3,p);}}
  // hero pixel sprite
  p.setColor(Color.rgb(50,50,70));c.drawRect(px-16,py-22,px+16,py+20,p);p.setColor(Color.rgb(235,185,145));c.drawRect(px-12,py-34,px+12,py-12,p);p.setColor(Color.rgb(80,35,120));c.drawRect(px-15,py-40,px+15,py-32,p);p.setColor(Color.WHITE);c.drawRect(px-8,py-29,px-3,py-24,p);c.drawRect(px+3,py-29,px+8,py-24,p);
  // UI
  p.setColor(0xaa000000);c.drawRect(12,12,370,78,p);txt(c,"LEVEL "+level+"   EXP "+xp,25,38,20);txt(c,"SỨC MẠNH: VÔ HẠN",25,64,18);txt(c,msg,400,40,20);
  // joystick
  p.setColor(0x55333333);c.drawCircle(joyX,joyY,72,p);p.setColor(0x99dddddd);c.drawCircle(joyX,joyY,28,p);
  // attack / ultimate
  p.setColor(0x88400000);c.drawCircle(w-145,h-130,58,p);txt(c,"ĐÁNH",w-176,h-125,18);
  p.setColor(0x88404090);c.drawCircle(w-280,h-105,70,p);txt(c,"TỐI THƯỢNG",w-338,h-100,16);
  if(flash>0){p.setColor(0x88ffffff);c.drawRect(0,0,w,h,p);flash-=.05f;}
  invalidate(); }
 public boolean onTouchEvent(android.view.MotionEvent e){float x=e.getX(),y=e.getY(); if(e.getAction()==MotionEvent.ACTION_DOWN||e.getAction()==MotionEvent.ACTION_MOVE){
   float w=getWidth(),h=getHeight();
   if(x<240&&y>h-220){joyX=Math.max(55,Math.min(185,x));joyY=Math.max(h-185,Math.min(h-55,y));joy=true; return true;}
   if(e.getAction()==MotionEvent.ACTION_DOWN && x>w-350 && x<w-210 && y>h-180){ultimate();return true;}
   if(e.getAction()==MotionEvent.ACTION_DOWN && x>w-210 && y>h-210){attack();return true;}
  } if(e.getAction()==MotionEvent.ACTION_UP){joy=false;joyX=120;joyY=getHeight()-100;} return true; }
 void attack(){Enemy hit=nearest(120); if(hit!=null){hit.alive=false;xp+=100;msg="💥 QUÁI BỊ HẠ GỤC!"; if(xp>=level*500){xp=0;level++;msg="LEVEL UP! SỨC MẠNH TĂNG!";}}}
 void ultimate(){for(Enemy e:enemies)e.alive=false;flash=1;xp+=1200;msg="☄ TUYỆT ĐỐI DIỆT THẾ GIỚI!"; if(xp>=level*500){level++;xp=0;}}
 Enemy nearest(float d){Enemy best=null;float bd=d;for(Enemy e:enemies)if(e.alive){float q=(float)Math.hypot(e.x-px,e.y-py);if(q<bd){bd=q;best=e;}}return best;}
 class Enemy{float x,y;boolean alive=true;Enemy(float a,float b){x=a;y=b;}}
                                                }
