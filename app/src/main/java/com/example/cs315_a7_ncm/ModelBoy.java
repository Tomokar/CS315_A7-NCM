package com.example.cs315_a7_ncm;

import com.google.gson.annotations.SerializedName;

public class ModelBoy
{
    @SerializedName("name")
    String gameCompanyName;
    @SerializedName("year")
    String gameCompanyYear;
    @SerializedName("recentConsole")
    String gameCompanyConsole;

    public ModelBoy(String name, String year, String console)
    {
        gameCompanyName = name;
        gameCompanyYear = year;
        gameCompanyConsole = console;
    }

    public String getGameCompanyName() { return gameCompanyName; }
    public String getGameCompanyYear() { return gameCompanyYear; }
    public String getGameCompanyConsole() { return gameCompanyConsole; }

    public void setGameCompanyName(final String gameCompanyName) { this.gameCompanyName = gameCompanyName; }
    public void setGameCompanyYear(final String gameCompanyYear) { this.gameCompanyYear = gameCompanyYear; }
    public void setGameCompanyConsole(final String gameCompanyConsole) { this.gameCompanyConsole = gameCompanyConsole; }
}
