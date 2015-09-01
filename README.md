# To Do List - with database

##### A customizable personal task list. Create a to-do list and save it on a local database. August 30, 2015.

#### By **name**

## Description

A user can add tasks to a database and specify parameters such as task type
(Household, Work, Fun, etc.). Users can also view all tasks in a particular category, and assign one task to multiple categories.

## Setup

* Please have all Java developer tools ready, including the JDK
* You'll need Gradle or some other way to run and compile Java
* This app uses Spark and Velocity for coordinating its front end
* You may use the database included with this file. Otherwise, create your own using the following information:

**In PSQL:**

* CREATE DATABASE to_do;
* \c to_do;
* CREATE TABLE tasks (id SERIAL PRIMARY KEY, description VARCHAR(50));
* CREATE TABLE categories (id SERIAL PRIMARY KEY, name VARCHAR(50));
* CREATE TABLE categories_tasks (id SERIAL PRIMARY KEY, category_id INT, task_id INT);
* CREATE DATABASE to_do_test WITH TEMPLATE to_do;

## Technologies Used

This app is written in Java with a little help from Bootstrap.

### Legal

Copyright (c) 2015 **Diana Holland**

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
