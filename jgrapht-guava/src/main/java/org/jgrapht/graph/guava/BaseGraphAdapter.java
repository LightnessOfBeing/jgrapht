/*
 * (C) Copyright 2018-2018, by Dimitrios Michail and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.jgrapht.graph.guava;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;
import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.graph.DefaultGraphType;

import com.google.common.graph.EndpointPair;

/**
 * A base abstract implementation for the graph adapter class using Guava's {@link Graph}. This
 * is a helper class in order to support both mutable and immutable graphs.
 * 
 * @author Dimitrios Michail
 *
 * @param <V> the graph vertex type
 * @param <G> type of the underlying Guava's graph
 */
public abstract class BaseGraphAdapter<V, G extends com.google.common.graph.Graph<V>>
    extends AbstractGraph<V, EndpointPair<V>>
    implements Graph<V, EndpointPair<V>>, Cloneable, Serializable
{
    private static final long serialVersionUID = -6742507788742087708L;

    protected static final String LOOPS_NOT_ALLOWED = "loops not allowed";

    protected transient Set<V> unmodifiableVertexSet = null;
    protected transient Set<EndpointPair<V>> unmodifiableEdgeSet = null;

    protected transient G graph;

    /**
     * Create a new adapter.
     * 
     * @param graph the graph
     */
    public BaseGraphAdapter(G graph)
    {
        this.graph = Objects.requireNonNull(graph);
    }

    @Override
    public EndpointPair<V> getEdge(V sourceVertex, V targetVertex)
    {
        if (sourceVertex == null || targetVertex == null) {
            return null;
        } else if (!graph.hasEdgeConnecting(sourceVertex, targetVertex)) {
            return null;
        } else {
            return createEdge(sourceVertex, targetVertex);
        }
    }

    @Override
    public EdgeFactory<V, EndpointPair<V>> getEdgeFactory()
    {
        return this::createEdge;
    }

    @Override
    public Set<V> vertexSet()
    {
        if (unmodifiableVertexSet == null) {
            unmodifiableVertexSet = Collections.unmodifiableSet(graph.nodes());
        }
        return unmodifiableVertexSet;
    }

    @Override
    public V getEdgeSource(EndpointPair<V> e)
    {
        return e.nodeU();
    }

    @Override
    public V getEdgeTarget(EndpointPair<V> e)
    {
        return e.nodeV();
    }

    @Override
    public GraphType getType()
    {
        return (graph.isDirected() ? new DefaultGraphType.Builder().directed()
            : new DefaultGraphType.Builder().undirected())
                .weighted(false).allowMultipleEdges(false)
                .allowSelfLoops(graph.allowsSelfLoops()).build();
    }

    @Override
    public boolean containsEdge(EndpointPair<V> e)
    {
        return graph.edges().contains(e);
    }

    @Override
    public boolean containsVertex(V v)
    {
        return graph.nodes().contains(v);
    }

    @Override
    public Set<EndpointPair<V>> edgeSet()
    {
        if (unmodifiableEdgeSet == null) {
            unmodifiableEdgeSet = Collections.unmodifiableSet(graph.edges());
        }
        return unmodifiableEdgeSet;
    }

    @Override
    public int degreeOf(V vertex)
    {
        return graph.degree(vertex);
    }

    @Override
    public Set<EndpointPair<V>> edgesOf(V vertex)
    {
        return graph.incidentEdges(vertex);
    }

    @Override
    public int inDegreeOf(V vertex)
    {
        return graph.inDegree(vertex);
    }

    @Override
    public Set<EndpointPair<V>> incomingEdgesOf(V vertex)
    {
        return graph
            .predecessors(vertex).stream().map(other -> createEdge(other, vertex))
            .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
    }

    @Override
    public int outDegreeOf(V vertex)
    {
        return graph.outDegree(vertex);
    }

    @Override
    public Set<EndpointPair<V>> outgoingEdgesOf(V vertex)
    {
        return graph
            .successors(vertex).stream().map(other -> createEdge(vertex, other))
            .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
    }

    @Override
    public double getEdgeWeight(EndpointPair<V> e)
    {
        if (e == null) { 
            throw new NullPointerException();
        } else if (!graph.hasEdgeConnecting(e.nodeU(), e.nodeV())) { 
            throw new IllegalArgumentException("no such edge in graph: " + e.toString());
        } else { 
            return Graph.DEFAULT_EDGE_WEIGHT;
        }
    }

    @Override
    public Set<EndpointPair<V>> getAllEdges(V sourceVertex, V targetVertex)
    {
        if (sourceVertex == null || targetVertex == null
            || !graph.nodes().contains(sourceVertex)
            || !graph.nodes().contains(targetVertex))
        {
            return null;
        } else if (!graph.hasEdgeConnecting(sourceVertex, targetVertex)) {
            return Collections.emptySet();
        } else {
            return Collections.singleton(createEdge(sourceVertex, targetVertex));
        }
    }

    /**
     * Create an edge. 
     * 
     * @param s the source vertex
     * @param t the target vertex
     * @return the edge
     */
    final EndpointPair<V> createEdge(V s, V t)
    {
        return graph.isDirected() ? EndpointPair.ordered(s, t) : EndpointPair.unordered(s, t);
    }

}
