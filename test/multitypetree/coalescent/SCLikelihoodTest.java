/*
 * Copyright (C) 2012 Tim Vaughan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package multitypetree.coalescent;

import org.junit.Test;

import beast.base.inference.parameter.RealParameter;
import junit.framework.TestCase;
import multitypetree.distributions.StructuredCoalescentTreeDensity;
import multitypetree.evolution.tree.MultiTypeTreeFromNewick;
import multitypetree.evolution.tree.SCMigrationModel;
import multitypetree.evolution.tree.TypeSet;

/**
 * Tests for StructuredCoalescentLikelihood class methods.
 *
 * @author Tim Vaughan
 */
public class SCLikelihoodTest extends TestCase {

	/**
	 * Test of calculateLogP method, of class StructuredCoalescentLikelihood.
	 */
	@Test
	public void testCalculateLogP() throws Exception {
		System.out.println("SCLikelihoodTest");

		// Assemble test MultiTypeTree:
		String newickStr =
                        "(((A[&state=1]:0.25)[&state=0]:0.25,B[&state=0]:0.5)[&state=0]:1.5,"
                        + "(C[&state=0]:1.0,D[&state=0]:1.0)[&state=0]:1.0)[&state=0]:0.0;";

		MultiTypeTreeFromNewick mtTree = new MultiTypeTreeFromNewick();
		mtTree.initByName(
                        "value", newickStr,
                        "typeLabel", "state");

		// Assemble migration model:
		RealParameter rateMatrix = new RealParameter();
		rateMatrix.initByName("value","2.0 1.0");
		RealParameter popSizes = new RealParameter();
		popSizes.initByName("value","5.0 10.0");
		SCMigrationModel migrationModel = new SCMigrationModel();
		migrationModel.initByName(
                        "rateMatrix", rateMatrix,
                        "popSizes", popSizes,
				"typeSet", new TypeSet("A", "B"));

		// Set up likelihood instance:
		StructuredCoalescentTreeDensity likelihood = new StructuredCoalescentTreeDensity();
		likelihood.initByName(
                        "migrationModel", migrationModel,
                        "multiTypeTree", mtTree);

		double expResult = -16.52831; // Calculated by hand
		double result = likelihood.calculateLogP();

		System.out.println(result);
		assertEquals(expResult, result, 1e-5);
		
		
		
	}

}
