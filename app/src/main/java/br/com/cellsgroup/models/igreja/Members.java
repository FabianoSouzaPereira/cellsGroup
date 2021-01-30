package br.com.cellsgroup.models.igreja;

public class Members {
    private String members = "";

    public Members(){ }

    public Members(String members){
        this.members = members;
    }

    public String getMembers ( ) {
        return members;
    }

    public void setMembers( String members) {
        this.members = members;
    }
}
