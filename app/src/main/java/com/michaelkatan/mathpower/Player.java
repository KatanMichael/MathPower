package com.michaelkatan.mathpower;

/**
 * Created by MichaelKatan on 06/01/2018.
 */

public class Player {
    private String _name = "";
    private String _id = "";
    private int _age;

    private String _email = "";

    private int _score;
    private int _lives;

    public Player(String _email, String id) {
        this._email = _email;
        this._id = id;

    }

    public Player() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public int get_score() {
        return _score;
    }

    public void set_score(int _score) {
        this._score = _score;
    }

    public int get_lives() {
        return _lives;
    }

    public void set_lives(int _lives) {
        this._lives = _lives;
    }


    @Override
    public String toString() {
        String ret;
        ret = ("Name: " + get_name() + ", Age:" + get_age() + ", Score: " + get_score());
        return ret;
    }
}
