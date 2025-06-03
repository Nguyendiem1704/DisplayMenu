package com.example.displaymenu;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.example.displaymenu.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;



public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ActionMode actionMode;
    TextView txtDescription, textCAB;
    Button btnContext, btnPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDescription = findViewById(R.id.txtDescription);
        btnContext = findViewById(R.id.btnContext);
        btnPopup = findViewById(R.id.btnPopup);

        registerForContextMenu(btnContext);

        btnPopup.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(MainActivity.this, v);
            popup.setOnMenuItemClickListener(this);
            popup.inflate(R.menu.popup_menu);
            popup.show();
        });

        txtDescription.setOnLongClickListener(v -> {
            if (actionMode != null) return false;
            actionMode = startSupportActionMode(actionModeCallback);
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }



    private void hideAll() {
        btnContext.setVisibility(View.GONE);
        btnPopup.setVisibility(View.GONE);
        textCAB.setVisibility(View.GONE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, 1, 0, "Upload");
        menu.add(0, 2, 0, "Search");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this, "Context Menu: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Popup Menu: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_menu, menu);
            mode.setTitle("Choose Action");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Toast.makeText(MainActivity.this, "CAB: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };
}
