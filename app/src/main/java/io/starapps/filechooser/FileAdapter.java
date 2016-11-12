package io.starapps.filechooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kush on 12/11/16.
 * FileChooser
 */

class FileAdapter extends BaseAdapter {

    private ArrayList<File> mFilesList = new ArrayList<>();
    private LayoutInflater mInflater;


    FileAdapter(Context context, ArrayList<File> filesList) {
        this.mFilesList = filesList;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mFilesList.size();
    }

    @Override
    public File getItem(int position) {
        return mFilesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_list_import_local_files, parent , false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        File file = mFilesList.get(position);
        holder.bind(file);

        return convertView;
    }

    private static class ViewHolder {

        ImageView fileTypeImage;
        TextView fileName;

        ViewHolder(View view) {
            this.fileTypeImage = (ImageView) view.findViewById(R.id.image_file_type);
            this.fileName = (TextView) view.findViewById(R.id.value_file_name);
        }

        void bind(File file) {
            if (file.isDirectory()) {
                fileTypeImage.setImageResource(R.drawable.ic_folder_black_24dp);
            } else {
                fileTypeImage.setImageResource(R.drawable.ic_description_black_24dp);
            }
            fileName.setText(file.getName());
        }
    }
}
