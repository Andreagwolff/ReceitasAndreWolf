package com.example.receitas.Util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Imagens {

    //Converte e reajusta a imagem para 400dp para um upload mais rápido ao DB
    public static Bitmap descodificarUri(Context context, Uri imagemSelecionada, int tamanhoRequerido) {
        try {
            // Decodifica tamanho da imagem
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imagemSelecionada), null, options);

            // Procura a escala correta.
            int larguraTmp = options.outWidth;
            int alturaTmp = options.outHeight;
            int escala = 1;

            while (true) {
                if (larguraTmp / 2 < tamanhoRequerido || alturaTmp / 2 < tamanhoRequerido) {
                    break;
                }
                larguraTmp /= 2;
                alturaTmp /= 2;
                escala *= 2;
            }

            // Decodifica com inSampleSize
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = escala;
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imagemSelecionada), null, options2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap converterParaBitmap(byte[] byteImagem){
        return BitmapFactory.decodeByteArray(byteImagem, 0, byteImagem.length);
    }

    //Converter bitmap para byte
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static byte[] converterBmpParaByte(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Uri getUriCompartilhamento(Bitmap bmpReceita, String titulo, String texto, Context context) {

        //Converter bmp para mutavel
        Bitmap bmpReceitaMutable = bmpReceita.copy(Bitmap.Config.ARGB_8888, true);

        //Fonte dos textos
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Nabila.ttf");

        //Config texto da receita
        TextPaint textPaintTexto = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaintTexto.setStyle(Paint.Style.FILL);
        textPaintTexto.setColor(Color.DKGRAY);
        textPaintTexto.setTextSize(15);
        textPaintTexto.setTypeface(typeface);

        //Config texto do título da receita
        TextPaint textPaintNome = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaintNome.setStyle(Paint.Style.FILL);
        textPaintNome.setColor(Color.WHITE);
        textPaintNome.setTextSize(30);
        textPaintNome.setTypeface(typeface);

        //Layout textos
        StaticLayout mTextLayout = new StaticLayout(texto, textPaintTexto, bmpReceitaMutable.getWidth()-5, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        StaticLayout mTextLayoutNome = new StaticLayout(titulo, textPaintNome, bmpReceitaMutable.getWidth()-5, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        //Canvas draw retangulo
        Canvas canvas = new Canvas(bmpReceitaMutable);

        Paint paintrect = new Paint();
        paintrect.setColor(Color.BLACK);
        paintrect.setStyle(Paint.Style.FILL_AND_STROKE);
        paintrect.setStrokeWidth(10);
        paintrect.setAlpha(127);

        canvas.drawRect(0, 5, bmpReceitaMutable.getWidth(), mTextLayoutNome.getHeight(), paintrect);

        //Canvas do bmp mutavel
        canvas = new Canvas(bmpReceitaMutable);

        //Set canvas titulo
        canvas.translate(5, 5);
        mTextLayoutNome.draw(canvas);

        int larguraBmpFinal = bmpReceitaMutable.getWidth();
        int alturaBmpFinal = mTextLayout.getHeight()+bmpReceitaMutable.getHeight()+5;

        //Bitmap que irá conter a união do bmp passado como argumento mais o final
        Bitmap bitmapFinal = Bitmap.createBitmap(larguraBmpFinal, alturaBmpFinal, Bitmap.Config.ARGB_8888);
        bitmapFinal.eraseColor(Color.WHITE);

        //Canvas do bmp final
        canvas = new Canvas(bitmapFinal);

        //União dos bmp
        canvas.drawBitmap(bmpReceitaMutable, 0f, 0f, null);
        canvas.drawBitmap(bitmapFinal, bmpReceitaMutable.getWidth(), 0f, null);

        //Config texto no bmpFinal (ingredientes, modo preparo, etc...)
        canvas.save();
        canvas.translate(5, bmpReceitaMutable.getHeight()+5);
        mTextLayout.draw(canvas);
        canvas.restore();

        Uri bmpUri = null;

        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmapFinal.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
