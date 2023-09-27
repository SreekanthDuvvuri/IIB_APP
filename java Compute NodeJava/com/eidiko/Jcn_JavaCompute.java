package com.eidiko;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;

public class Jcn_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			MbMessage outMessage = new MbMessage(inMessage);
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			Connection conn = getJDBCType4Connection("jcn", JDBC_TransactionType.MB_TRANSACTION_AUTO);
            
			//statement
	    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			// result set
		ResultSet rs = stmt.executeQuery("Select * from mapping");
		MbElement outRoot = outAssembly.getMessage().getRootElement().createElementAsLastChild(MbXMLNSC.PARSER_NAME).createElementAsLastChild(MbElement.TYPE_NAME, "Response",null );
		  while(rs.next()){
			
			String name = null;
			String id = null;
			name =rs.getString(1);
			id = rs.getString(2);
		
			outRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"name",name);
			outRoot.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"id",id);
			
		  }

			
		} catch (MbException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new MbUserException(this, "evaluate()", "", "", e.toString(), null);
		}
		out.propagate(outAssembly);

	}

	
	@Override
	public void onPreSetupValidation() throws MbException {
	}


	 
	@Override
	public void onSetup() throws MbException {
	}

	
	@Override
	public void onStart() throws MbException {
	}


	@Override
	public void onTearDown() throws MbException {
	}

}
