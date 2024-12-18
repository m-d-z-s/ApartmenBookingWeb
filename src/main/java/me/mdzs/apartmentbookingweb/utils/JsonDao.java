package me.mdzs.apartmentbookingweb.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;

public interface JsonDao<T> {
    static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static List readJsonToList(String filePath) throws IOException {
        return null;
    }

    static void writeListToJson(List list, String filePath) throws IOException {

    }
}
