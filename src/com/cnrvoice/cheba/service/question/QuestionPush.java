package com.cnrvoice.cheba.service.question;

import org.springframework.web.client.RestTemplate;

import com.cnrvoice.cheba.entity.vo.QuestionVo;
import com.cnrvoice.cheba.service.question.http.ConfigHolder;
import com.cnrvoice.cheba.service.question.http.ResourceInvoker;

public class QuestionPush
{
	private static ResourceInvoker invoker = new ResourceInvoker(
			new ConfigHolder(new RestTemplate()));
	
	public static void push(QuestionVo questionVo)
	{
		invoker.getEntityFromPost("", invoker.createJSONEntity(questionVo),
				null);
	}
}
