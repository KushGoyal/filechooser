package io.starapps.filechooser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SelectFileActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener{
    private FileAdapter mAdapter;
    private Button mBackButton;
    private File mCurrentDir, mParentDir;
    private ArrayList<File> mFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);
        ListView mListView = (ListView) findViewById(R.id.list_files);
        mBackButton = (Button) findViewById(R.id.button_parent_dir);
        mBackButton.setOnClickListener(this);
        mAdapter = new FileAdapter(this, mFiles);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mCurrentDir = Environment.getExternalStorageDirectory();
        mParentDir = null;
        mBackButton.setText(mCurrentDir.getPath());
        getFileList(mCurrentDir);
    }

    public void getFileList(File dir) {
        if (dir != null && dir.listFiles() != null) {
            mFiles.clear();
            mFiles.addAll(Arrays.asList(dir.listFiles()));
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        final File file = mFiles.get(position);
        if (file.isDirectory()) {
            mParentDir = mCurrentDir;
            mCurrentDir = file;
            mBackButton.setText(mCurrentDir.getPath());
            getFileList(mCurrentDir);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.title_dialog_select_file);
            builder.setMessage("Select " + file.getName() + " ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.FILE_PATH, file.getAbsolutePath());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

    void goBack() {
        if (mParentDir == null || mCurrentDir.getParentFile() == null) {
            finish();
        } else {
            // set parent
            mParentDir = mCurrentDir.getParentFile();
            // set current dir
            mCurrentDir = mParentDir;
            // change file list
            getFileList(mCurrentDir);
            // change back button text
            mBackButton.setText(mCurrentDir.getPath());
        }
    }

    @Override
    public void onClick(View view) {
        goBack();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}
