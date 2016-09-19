package com.nintersoft.ninternav;

import android.graphics.Bitmap;

/**
 * Created by Mauro on 17/09/2016.
 */
public class ItemPersonalizado
{
    protected String texto;
    protected int iconeRid;
    protected Bitmap imagem;

    public ItemPersonalizado()
    {
    }

    public ItemPersonalizado(String texto, int iconeRid)
    {
        this.texto = texto;
        this.iconeRid = iconeRid;
        this.imagem = null;
    }

    public ItemPersonalizado(String texto, Bitmap imagem)
    {
        this.texto = texto;
        this.iconeRid = -1;
        this.imagem = imagem;//Bitmap.createScaledBitmap(imagem, 60, 60, false);
    }

    public int getIconeRid()
    {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid)
    {
        this.iconeRid = iconeRid;
    }

    public void setImagem(Bitmap imagem)
    {
        this.imagem = imagem;//Bitmap.createScaledBitmap(imagem, 60, 60, false);
        this.iconeRid = -1;
    }

    public Bitmap getImagem()
    {
        return this.imagem;
    }

    public String getTexto()
    {
        return texto;
    }

    public void setTexto(String texto)
    {
        this.texto = texto;
    }
}
