package com.artem_tereshko.androidproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import game.AbstractGameObject;
import game.Level;
import game.PhysicsEngine;
import game.object.circle.Ball;
import game.object.circle.Bullet;
import game.object.circle.Hole;
import game.object.rectangle.Cannon;

//import game.object.circle.Bullet;
//import game.object.rectangle.Cannon;


public class GraphicsEngine extends SurfaceView implements SurfaceHolder.Callback, GameListener {

    GameListener gameListener;
    private DrawThread drawThread;
    PhysicsEngine engine;
    MediaPlayer mp;

    Paint gradientPaint;

    //region background features
    int background_gradient_colors[] = new int[3];
    float background_gradient_colors_positions[] = new float[3];
    //endregion

    int levelIndex = 0;

    public GraphicsEngine(Context context,SensorManager manager, int displayRotaion, int levelIndex) {
        super(context);

        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.RGBA_8888);

        this.levelIndex = levelIndex;

        background_gradient_colors_positions[0] = 0;
        background_gradient_colors_positions[1] = 0.5f;
        background_gradient_colors_positions[2] = 1;

        background_gradient_colors[0] = Color.parseColor("#FF003333");
        background_gradient_colors[1] = Color.parseColor("#FF05C1FF");
        background_gradient_colors[2] = Color.parseColor("#FF003333");

        gradientPaint = new Paint();

        engine = new PhysicsEngine(manager, displayRotaion);
        engine.setGameListener(this);

        mp = MediaPlayer.create(context, R.raw.glass);

    }
    public void SetGameListener(GameListener l){
        gameListener = l;
    }
    public void DeleteGameListener(){
        gameListener = null;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        //instancie objet bille
        if (levelIndex == 1 )
        engine.setLevel(Level.Level1(getHeight(), getWidth()));
        else if(levelIndex == 2)
            engine.setLevel(Level.Level2(getHeight(), getWidth()));
        else if (levelIndex == 3)
            engine.setLevel(Level.Level3(getHeight(), getWidth()));

        //chargement de la ressource
        Bitmap balltexture = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        Bitmap holeTexture = BitmapFactory.decodeResource(getResources(), R.drawable.hole);
        Bitmap cannonTexture = BitmapFactory.decodeResource(getResources(), R.drawable.canon);//texture extraite de http://furiouspixels.blogspot.fr/
        for (AbstractGameObject gameObject : engine.getLevel().get_objets()){
            //Hole
            if (gameObject.getAbstractGameObject() instanceof Hole) {
                gameObject.set_texture(holeTexture);
            }
            //Ball
            if (gameObject.getAbstractGameObject() instanceof Ball) {
                gameObject.set_texture(balltexture);
                gameObject.set_player(mp);
            }
            //Cannon
            if (gameObject.getAbstractGameObject() instanceof Cannon) {
                gameObject.set_texture(cannonTexture);
            }
            //Bullet
            if (gameObject.getAbstractGameObject() instanceof Bullet) {
                gameObject.set_texture(balltexture);
            }
        }
        //start accelerometer
        engine.Start();
        //---------------------- a tester sans
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas(null);

            gradientPaint.setShader(new LinearGradient(0, canvas.getHeight(), canvas.getWidth(), 0, background_gradient_colors, background_gradient_colors_positions, Shader.TileMode.CLAMP));
        } finally {
            if (canvas != null) {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
        //----------------------
        //start drawer
        drawThread = new DrawThread(getHolder(), this);
        drawThread.setRunning(true);
        drawThread.start();

        
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        
    }

    protected void Draw(Canvas canvas) {    //appelé à chaque update

        canvas.drawPaint(gradientPaint);

        for (AbstractGameObject gameObject : engine.getLevel().get_objets()){
                gameObject.Draw(canvas);
        }
    }


    public void Pause(){
        engine.Pause();
    }

    public void Stop(){
        engine.Stop();
    }

    public void Start() {
     //   engine.Start();

    }

    @Override
    public void onGameWin() {
        this.gameListener.onGameWin();
    }

    @Override
    public void onGameLose() {
        this.gameListener.onGameLose();
    }

    public class DrawThread extends Thread {


        private boolean running = false;
        private SurfaceHolder surfaceHolder;
        private GraphicsEngine v;



        public DrawThread(SurfaceHolder surfaceHolder, GraphicsEngine v) {
            this.v= v;

            this.surfaceHolder = surfaceHolder;
        }


        public void setRunning(boolean running) {
            this.running = running;
        }


        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;

                    synchronized (surfaceHolder) {
                        v.Draw(canvas);
                    }

                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
