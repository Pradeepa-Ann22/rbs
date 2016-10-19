package com.pr.rbs.rbs_core.jpa;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class RespectfulImprovedNamingStrategy extends ImprovedNamingStrategy
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2777620002932018039L;

	@Override
    public String columnName(String columnName)
    {
        return columnName;
    }

    @Override
    public String tableName(String tableName)
    {
        return tableName;
    }
}