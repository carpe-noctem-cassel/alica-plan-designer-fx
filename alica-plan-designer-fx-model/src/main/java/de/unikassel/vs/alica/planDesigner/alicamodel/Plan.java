package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plan extends AbstractPlan {

    protected final SimpleBooleanProperty masterPlan = new SimpleBooleanProperty();
    protected final SimpleDoubleProperty utilityThreshold = new SimpleDoubleProperty();
    protected final ObjectProperty<PreCondition> preCondition = new SimpleObjectProperty<>();
    protected final ObjectProperty<RuntimeCondition> runtimeCondition = new SimpleObjectProperty<>();

    protected final ArrayList<EntryPoint> entryPoints = new ArrayList<>();
    protected final ArrayList<State> states = new ArrayList<>();
    protected final ArrayList<Transition> transitions = new ArrayList<>();
    protected final ArrayList<Synchronisation> synchronisations = new ArrayList<>();

    /**
     * Need to be explicitly written, because the constructor Plan(long id)
     * makes the autogenerated version of this one vanishing.
     */
    public Plan() {
        super();
    }

    /**
     * Constructor for representing incomplete Plan References while parsing files.
     * @param id The ID found in the file, while parsing the reference.
     */
    public Plan(long id) {
        this();
        this.id = id ;
    }

    public double getUtilityThreshold() {
        return utilityThreshold.get();
    }
    public void setUtilityThreshold(double utilityThreshold) {
        this.utilityThreshold.set(utilityThreshold);
    }
    public SimpleDoubleProperty utilityThreshold() {
        return utilityThreshold;
    }

    public boolean getMasterPlan() {
        return masterPlan.get();
    }
    public void setMasterPlan(boolean masterPlan) {
        this.masterPlan.set(masterPlan);
    }
    public SimpleBooleanProperty masterPlanProperty() {
        return masterPlan;
    }

    public PreCondition getPreCondition() {
        return preCondition.get();
    }
    public void setPreCondition(PreCondition preCondition) {
        this.preCondition.set(preCondition);
        if(preCondition != null){
            InvalidationListener dirty = obs -> this.setDirty(true);
            preCondition.nameProperty().addListener(dirty);
            preCondition.conditionStringProperty().addListener(dirty);
            preCondition.enabledProperty().addListener(dirty);
            preCondition.pluginNameProperty().addListener(dirty);
            preCondition.commentProperty().addListener(dirty);
        }
    }
    public ObjectProperty<PreCondition> preConditionProperty(){
        return preCondition;
    }

    public RuntimeCondition getRuntimeCondition() {
        return runtimeCondition.get();
    }
    public void setRuntimeCondition(RuntimeCondition runtimeCondition) {
        this.runtimeCondition.set(runtimeCondition);
        if(runtimeCondition != null){
            InvalidationListener dirty = obs -> this.setDirty(true);
            runtimeCondition.nameProperty().addListener(dirty);
            runtimeCondition.conditionStringProperty().addListener(dirty);
            runtimeCondition.enabledProperty().addListener(dirty);
            runtimeCondition.pluginNameProperty().addListener(dirty);
            runtimeCondition.commentProperty().addListener(dirty);
        }
    }
    public ObjectProperty<RuntimeCondition> runtimeConditionProperty(){
        return runtimeCondition;
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
        this.setDirty(true);
    }
    public void removeTransition(Transition transition) {
        transitions.remove(transition);
        this.setDirty(true);
    }
    public List<Transition> getTransitions() {
        return Collections.unmodifiableList(transitions);
    }

    public void addState(State state) {
        states.add(state);
        this.setDirty(true);
    }
    public void removeState(State state) {
        states.remove(state);
        this.setDirty(true);
    }
    public List<State> getStates() {
        return Collections.unmodifiableList(states);
    }

    public void addSynchronization(Synchronisation synchronisation) {
        synchronisations.add(synchronisation);
        synchronisation.dirtyProperty().addListener((observable, oldValue, newValue) -> {
            this.setDirty(true);
        });
        this.setDirty(true);
    }
    public void removeSynchronization(Synchronisation synchronisation) {
        synchronisations.remove(synchronisation);
        this.setDirty(true);
    }
    public List<Synchronisation> getSynchronisations() {
        return Collections.unmodifiableList(synchronisations);
    }

    public void addEntryPoint(EntryPoint entryPoint) {
        entryPoints.add(entryPoint);
        this.setDirty(true);
    }
    public void removeEntryPoint(EntryPoint entryPoint) {
        entryPoints.remove(entryPoint);
        this.setDirty(true);
    }
    public List<EntryPoint> getEntryPoints() {
        return Collections.unmodifiableList(entryPoints);
    }

    @Override
    public void registerDirtyFlag() {
        super.registerDirtyFlag();
        masterPlan.addListener(         (observable, oldValue, newValue) -> this.setDirty(true));
        utilityThreshold.addListener(   (observable, oldValue, newValue) -> this.setDirty(true));
        preCondition.addListener(       (observable, oldValue, newValue) -> this.setDirty(true));
        runtimeCondition.addListener(   (observable, oldValue, newValue) -> this.setDirty(true));

        for (Synchronisation synchronisation : synchronisations) {
            synchronisation.dirtyProperty().addListener((observable, oldValue, newValue) -> {
                this.setDirty(true);
            });
        }

        for (Variable variable : variables) {
            variable.nameProperty().addListener((observable, oldValue, newValue) -> {
                this.setDirty(true);
            });
            variable.commentProperty().addListener((observable, oldValue, newValue) -> {
                this.setDirty(true);
            });
            variable.variableTypeProperty().addListener((observable, oldValue, newValue) -> {
                this.setDirty(true);
            });
        }

        this.setDirty(false);
    }
}
