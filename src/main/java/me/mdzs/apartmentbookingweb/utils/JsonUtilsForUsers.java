package me.mdzs.apartmentbookingweb.utils;

import me.mdzs.apartmentbookingweb.domain.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtilsForUsers implements JsonDao<User> {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<User> readJsonToList(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(reader, userListType);
        }
    }

    public static void writeListToJson(List<User> list, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(list, writer);
        }
    }
}


