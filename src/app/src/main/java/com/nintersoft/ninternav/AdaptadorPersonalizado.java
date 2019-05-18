package com.nintersoft.ninternav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorPersonalizado extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<ItemPersonalizado> itens;

    public AdaptadorPersonalizado(Context context, ArrayList<ItemPersonalizado> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Retorna a quantidade de itens
     *
     * @return
     */
    public int getCount()
    {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public ItemPersonalizado getItem(int position)
    {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        //Pega o item de acordo com a posção.
        ItemPersonalizado item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.item_lista_aba, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.text)).setText(item.getTexto());
        if (item.getIconeRid() != -1) ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());
        else ((ImageView) view.findViewById(R.id.imagemview)).setImageBitmap(item.getImagem());

        return view;
    }
}
