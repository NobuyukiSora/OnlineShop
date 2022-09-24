package com.example.projekakhirmcs;

import java.util.Vector;

public class Database {
    private static Vector<Barang> databarang = new Vector<>();
    public static Vector<Barang> getDatabarang(){
        return databarang;
    }
    public static void setDatabarang(Vector<Barang> databarang){
        Database.databarang = databarang;
    }
}

