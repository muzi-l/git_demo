/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.base.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.cnrvoice.base.mongo.MongoFactoryBean;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

public abstract class GenericMongoGfsDao extends MongoGfsDaoSupport
{
	public abstract String getFileType();
	
	public GridFSDBFile find(ObjectId id)
	{
		GridFS fs = getGridFS(this.getFileType());
		return fs.find(id);
	}
	
	public List<GridFSDBFile> find(String filename)
	{
		GridFS fs = getGridFS(this.getFileType());
		return fs.find(filename);
	}
	
	public OutputStream findStream(ObjectId id) throws IOException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		GridFSDBFile gfsFile = find(id);
		gfsFile.writeTo(output);
		
		return output;
	}
	
	public String create(File file) throws IOException
	{
		GridFS fs = getGridFS(this.getFileType());
		GridFSFile gridFile = fs.createFile(file);
		gridFile.save();
		return gridFile.getId().toString();
	}
	
	public String create(String filename, InputStream in) throws IOException
	{
		GridFS fs = getGridFS(this.getFileType());
		GridFSFile gridFile = fs.createFile(in, filename);
		gridFile.save();
		return gridFile.getId().toString();
	}
	
	public void remove(ObjectId id)
	{
		GridFS fs = getGridFS(this.getFileType());
		fs.remove(id);
	}
	
	public void remove(String filename)
	{
		GridFS fs = getGridFS(this.getFileType());
		fs.remove(filename);
	}
	
	private GridFS getGridFS(String fileType)
	{
		DB db = getDatabase();
		return new GridFS(db, fileType);
	}
	
	@Autowired
	public void setMongoFactoryAutowired(MongoFactoryBean mongoFactory)
			throws Exception
	{
		super.setMongoFactory(mongoFactory);
	}
}
