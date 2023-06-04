package com.ozank.cpathway.fluxgraph;

import com.ozank.cpathway.simulation.Matrix;
import com.ozank.cpathway.simulation.TripleIndex;

public class FluxGraph {
    private Matrix<TripleIndex> fluxes;

    public FluxGraph(Matrix<TripleIndex> fluxes) {
        this.fluxes = fluxes;
    }

    public Matrix<TripleIndex> getFluxes() {
        return fluxes;
    }
}
