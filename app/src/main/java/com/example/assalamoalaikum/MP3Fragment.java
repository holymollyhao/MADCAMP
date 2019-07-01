package com.example.assalamoalaikum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MP3Fragment extends Fragment {
    ListView myListViewForSongs;
    String[] items;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_music, container, false);
        System.out.println("*****");
        myListViewForSongs = (ListView) v.findViewById(R.id.mySongListView);
        imageView=(ImageView) getActivity().findViewById(R.id.img);
        System.out.println(myListViewForSongs);
        Dexter.withActivity(this.getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());

                        items = new String[mySongs.size()];

                        for(int i=0; i<mySongs.size();i++){

                            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");

                        }

                        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,items);
                        myListViewForSongs.setAdapter(myAdapter);

                        myListViewForSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                String songName = myListViewForSongs.getItemAtPosition(i).toString();

                                startActivity(new Intent(getActivity().getApplicationContext(),PlayerActivity.class).putExtra("songs",mySongs).putExtra("songname",songName).putExtra("pos",i));

                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                }).check();

        return v;
    }

   /* public void runtimePermission(){

        Dexter.withActivity(this.getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());

                        items = new String[mySongs.size()];

                        for(int i=0; i<mySongs.size();i++){

                            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");

                        }

                        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,items);
                        myListViewForSongs.setAdapter(myAdapter);

                        myListViewForSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                String songName = myListViewForSongs.getItemAtPosition(i).toString();

                                startActivity(new Intent(getActivity().getApplicationContext(),PlayerActivity.class).putExtra("songs",mySongs).putExtra("songname",songName).putExtra("pos",i));

                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                }).check();
    }*/

    public ArrayList<File> findSong(File file){

        ArrayList<File> arrayList = new ArrayList<>();

        File[] files = file.listFiles();
        for(File singleFile: files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){

                arrayList.addAll(findSong(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp3")||singleFile.getName().endsWith(".wav")){
                    arrayList.add(singleFile);
                }
            }
        }

        return arrayList;

    }
}

    /*void display(){

        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());

        items = new String[mySongs.size()];

        for(int i=0; i<mySongs.size();i++){

            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");

        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1,items);
        myListViewForSongs.setAdapter(myAdapter);

        myListViewForSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String songName = myListViewForSongs.getItemAtPosition(i).toString();

                startActivity(new Intent(getActivity().getApplicationContext(),PlayerActivity.class).putExtra("songs",mySongs).putExtra("songname",songName).putExtra("pos",i));

            }
        });

    }
}*/
