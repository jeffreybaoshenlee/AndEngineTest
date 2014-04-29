package com.agilemobidev.andenginetest;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.os.Handler;

public class MainActivity extends BaseGameActivity {
	private static final float CAMERA_WIDTH = 480;
	private static final float CAMERA_HEIGHT = 320;

	private static int centerX;
	private static int centerY;

	private Camera camera;

	private BitmapTextureAtlas texture;
	private TextureRegion textureRegion;
	private Sprite sprite;

	private Handler handler;

	private Runnable changePic = new Runnable() {
		@Override
		public void run() {
			texture.clearTextureAtlasSources();
			BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture,
					MainActivity.this, "sprite1.png", 0, 0);
		}
	};

	@Override
	public Engine onLoadEngine() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions options = new EngineOptions(true,
				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);

		handler = new Handler();
		return new Engine(options);
	}

	@Override
	public void onLoadResources() {
		texture = new BitmapTextureAtlas(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				texture, this, "sprite0.png", 0, 0);
		centerX = (int) ((CAMERA_WIDTH - textureRegion.getWidth()) / 2);
		centerY = (int) ((CAMERA_HEIGHT - textureRegion.getHeight()) / 2);

		mEngine.getTextureManager().loadTexture(texture);
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		sprite = new Sprite(centerX, centerY, textureRegion);
		scene.attachChild(sprite);

		handler.postDelayed(changePic, 10000);
		return scene;
	}

	@Override
	public void onLoadComplete() {
	}
}