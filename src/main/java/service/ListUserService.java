package service;

import db.DataBase;
import utils.TemplateUtils;

import java.io.IOException;

public class ListUserService {

	public byte[] makeTemplateBodyByUser() throws IOException {
		return TemplateUtils.makeListUserTemplate(DataBase.findAll());
	}
}
