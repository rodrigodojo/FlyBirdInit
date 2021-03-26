package com.dojo.flybirdinit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlyBirdInit extends ApplicationAdapter {

	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture obstaculoBaixo;
	Texture obstaculoCima;
	Texture imagemFundo;
	Texture[] passaros;
	float posicaoX ;
	float posicaoY;
	float velocidade;
	float altura ;
	float vao;
	float contador;
	float gravidade;
	Random alturaRandom;
	Circle circuloPassaro;
	Rectangle retanguloCima;
	Rectangle retanguloBaixo;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		alturaRandom = new Random();
		obstaculoBaixo = new Texture("canobaixo.png");
		obstaculoCima = new Texture("canocima.png");
		imagemFundo = new Texture("imagemfundo.png");
		passaros = new Texture[6];
		for(int i=0; i<passaros.length ; i++){
			passaros[i] = new Texture("passaro"+String.valueOf(i+1)+".png") ;
		}
		circuloPassaro = new Circle();
		retanguloCima = new Rectangle();
		retanguloBaixo = new Rectangle();


		posicaoX = Gdx.graphics.getWidth();
		posicaoY = Gdx.graphics.getHeight()/2;
		altura = Gdx.graphics.getHeight()/2;
		vao = 300;
		velocidade =0;
		gravidade = 2;


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		posicaoX -= Gdx.graphics.getDeltaTime()*500;
		contador += Gdx.graphics.getDeltaTime()*10;

		if(posicaoX < -obstaculoBaixo.getWidth()){
			posicaoX = Gdx.graphics.getWidth();
			altura += (alturaRandom.nextFloat() - 0.5f) * 150;
		}
		if(contador>6){
			contador=0;
		}
		if(posicaoY > 0 || velocidade < 0){
			velocidade += gravidade;
			posicaoY -= velocidade* Gdx.graphics.getDeltaTime()*30;
		}
		if(Gdx.input.justTouched()){
			velocidade = -30;
		}


		batch.begin();
		batch.draw(imagemFundo,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(obstaculoBaixo, posicaoX, altura - vao/2 - obstaculoBaixo.getHeight());
		batch.draw(obstaculoCima,posicaoX,altura + vao/2 );
		batch.draw(passaros[((int)contador)], 50, posicaoY);
		batch.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);

		retanguloCima = new Rectangle(posicaoX, altura + vao/2, obstaculoCima.getWidth(), obstaculoCima.getHeight());
		retanguloBaixo = new Rectangle(posicaoX, altura - obstaculoBaixo.getHeight() - vao/2, obstaculoBaixo.getWidth(), obstaculoBaixo.getHeight());
		circuloPassaro.set(50 + passaros[0].getWidth() / 2, posicaoY + passaros[0].getWidth() / 2, passaros[0].getHeight() / 2);

		shapeRenderer.circle(circuloPassaro.x, circuloPassaro.y, circuloPassaro.radius);
		shapeRenderer.rect(posicaoX, altura + vao/2, obstaculoCima.getWidth(), obstaculoCima.getHeight());
		shapeRenderer.rect(posicaoX, altura - obstaculoBaixo.getHeight() - vao/2, obstaculoBaixo.getWidth(), obstaculoBaixo.getHeight());

		if (Intersector.overlaps(circuloPassaro, retanguloCima) || Intersector.overlaps(circuloPassaro, retanguloBaixo)) {
			Gdx.app.log("meuLog", "Colidiu!");
			/*
			if (estadoJogo==1) {
				somHit.play();
				estadoJogo=2;
			}

			 */
		}

		shapeRenderer.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
