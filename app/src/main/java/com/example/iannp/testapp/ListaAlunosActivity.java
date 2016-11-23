package com.example.iannp.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iannp.testapp.DAO.AlunoDAO;
import com.example.iannp.testapp.model.Aluno;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {


    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Button novoAluno = (Button) findViewById(R.id.lista_alunos_novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiProForm = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(vaiProForm);
            }
        });
         listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        registerForContextMenu(listaAlunos);

    }

    private void loadList() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem delete = menu.add("delete");
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
                Toast.makeText(ListaAlunosActivity.this, "Aluno" + aluno.getNome() + " deletado!", Toast.LENGTH_SHORT).show();
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();
                loadList();
                return false;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        loadList();

    }
}
