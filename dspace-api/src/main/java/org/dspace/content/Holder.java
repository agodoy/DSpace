package org.dspace.content;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="holder")
public class Holder extends DSpaceObject implements DSpaceObjectLegacySupport{

	@Override
	public Integer getLegacyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
