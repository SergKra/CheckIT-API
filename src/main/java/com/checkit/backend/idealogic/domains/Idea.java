package com.checkit.backend.idealogic.domains;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Idea {

        private long id;
        private String title;
        private String author; //USER_NAME or ID
        private Content content;
        private Status status;
        private Category ideaCategory;
        private String creationDate;
        private String description;

        public Idea(String title, String author, String description){

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
            Date date = new Date();

            this.title = title;
            this.author = author;
            this.ideaCategory = ideaCategory.LITERATURE;
            this.description = description;
            this.creationDate = dateFormat.format(date);
            this.status = Status.DRAFT;
        }

        public String getTitle(){
            return this.title;
        }

        public String getAuthor(){
            return this.author;
        }

        public String getCreationDate() {
            return this.creationDate;
        }

        public String getDescription() {
            return this.description;
        }

        public String getIdeaStatus() {
            return this.status.toString();
        }

        public void setDescription(String description){
            this.description = description;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public void setStatus(Status ideaStatus){
            this.status = ideaStatus;
        }
    }
