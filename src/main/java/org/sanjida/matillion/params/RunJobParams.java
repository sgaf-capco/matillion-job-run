package org.sanjida.matillion.params;

import java.util.Objects;

public class RunJobParams {

    String demoTableNameSuffix;
    String demoTableName;

    public RunJobParams() {
    }

    public RunJobParams(String demo_table_name_suffix, String demo_table_name) {
        this.demoTableNameSuffix = demo_table_name_suffix;
        this.demoTableName = demo_table_name;
    }

    public String getDemoTableNameSuffix() {
        return demoTableNameSuffix;
    }

    public void setDemoTableNameSuffix(String demoTableNameSuffix) {
        this.demoTableNameSuffix = demoTableNameSuffix;
    }

    public String getDemoTableName() {
        return demoTableName;
    }

    public void setDemoTableName(String demoTableName) {
        this.demoTableName = demoTableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunJobParams that = (RunJobParams) o;
        return Objects.equals(demoTableNameSuffix, that.demoTableNameSuffix) && Objects.equals(demoTableName, that.demoTableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(demoTableNameSuffix, demoTableName);
    }

    @Override
    public String toString() {
        return "RunJobParams{" +
                "demo_table_name_suffix='" + demoTableNameSuffix + '\'' +
                ", demo_table_name='" + demoTableName + '\'' +
                '}';
    }
}
