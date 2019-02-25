package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Variable;
import de.unikassel.vs.alica.planDesigner.deserialization.ExternalFileDeserializer;
import de.unikassel.vs.alica.planDesigner.deserialization.VariableDeserializer;
import de.unikassel.vs.alica.planDesigner.serialization.ExternalRefSerializer;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefKeySerializer;

public abstract class ParametrisationMixIn {
    @JsonSerialize(using = ExternalRefSerializer.class)
    @JsonDeserialize(using = ExternalFileDeserializer.class)
    protected AbstractPlan subPlan;

    @JsonSerialize(using = InternalRefKeySerializer.class)
    @JsonDeserialize(using = VariableDeserializer.class)
    protected Variable subVariable;

    @JsonSerialize(using = InternalRefKeySerializer.class)
    @JsonDeserialize(using = VariableDeserializer.class)
    protected Variable variable;
}
