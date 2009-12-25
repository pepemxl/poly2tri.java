package org.poly2tri.examples.ardor3d;

import java.util.ArrayList;

import org.poly2tri.Poly2Tri;
import org.poly2tri.triangulation.TriangulationPoint;
import org.poly2tri.triangulation.point.TPoint;
import org.poly2tri.triangulation.sets.ConstrainedPointSet;
import org.poly2tri.triangulation.tools.ardor3d.ArdorMeshMapper;
import org.poly2tri.triangulation.util.PointGenerator;

import com.ardor3d.example.ExampleBase;
import com.ardor3d.framework.FrameHandler;
import com.ardor3d.input.logical.LogicalLayer;
import com.ardor3d.math.ColorRGBA;
import com.ardor3d.renderer.state.WireframeState;
import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.hint.LightCombineMode;
import com.google.inject.Inject;

public class CDTUniformDistributionExample extends ExampleBase
{

    public static void main(final String[] args) 
    {
        start(CDTUniformDistributionExample.class);
    }

    @Inject
    public CDTUniformDistributionExample( LogicalLayer logicalLayer, FrameHandler frameHandler )
    {
        super( logicalLayer, frameHandler );
    }

    @Override
    protected void initExample()
    {       
        _canvas.setVSyncEnabled( true );
        
        _root.getSceneHints().setLightCombineMode( LightCombineMode.Off );
        _root.setRenderState( new WireframeState() );

        Mesh mesh = new Mesh();
        mesh.setDefaultColor( ColorRGBA.BLUE );
        _root.attachChild( mesh );
  
        double scale = 100;
        int size = 1000;
        int index = (int)(Math.random()*size);
        ArrayList<TriangulationPoint> points = PointGenerator.uniformDistribution( scale, size );

        // Lets add a constraint that cuts the uniformDistribution in half
        points.add( new TPoint(0,scale/2) );
        points.add( new TPoint(0,-scale/2) );
        index = size; 
        
        ConstrainedPointSet cps = new ConstrainedPointSet( points, new int[]{ index, index+1 } );
        Poly2Tri.triangulate( cps );
        ArdorMeshMapper.updateTriangleMesh( mesh, cps );
    }
}
