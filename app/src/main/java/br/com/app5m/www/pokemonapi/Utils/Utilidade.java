package br.com.app5m.www.pokemonapi.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class Utilidade {
    private Context context;
    private ProgressDialog mDialog;

    public Utilidade(Context context) {
        this.context = context;
        this.mDialog = new ProgressDialog(context);
        this.mDialog.setCancelable(false);
        this.mDialog.setMessage("Aguarde...");
    }

    public void abrirDialog() {
        if (mDialog != null) mDialog.show();
    }

    public void fecharDialog() {
        if (mDialog != null) if (mDialog.isShowing()) mDialog.dismiss();
    }

    public void abrirDialog(AlertDialog alertDialog) {
        if (alertDialog == null) return;
        if (!alertDialog.isShowing()) alertDialog.show();

    }

    public void fecharDialog(AlertDialog alertDialog) {
        if (alertDialog == null) return;
        if (alertDialog.isShowing()) alertDialog.dismiss();

    }

    public void mostrarToast(String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    public boolean tratarLista(View esconder, View recyclerView, List objectList) {
        if (objectList == null) {
            esconder.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return false;
        }
        if (objectList.size() == 0) {
            esconder.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return false;
        } else {
            esconder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public String tratarNome(String str) {
        if (str == null) {
            return "";
        }
        if (str.length() < 1) {
            return str;
        }
        String[] words = str.split("\\s");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            sb.append(words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString();
    }


}
