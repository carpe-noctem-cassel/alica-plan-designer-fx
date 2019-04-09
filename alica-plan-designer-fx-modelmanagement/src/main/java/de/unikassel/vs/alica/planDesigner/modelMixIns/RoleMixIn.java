package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.RoleSet;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.deserialization.RoleDeserializer;
import de.unikassel.vs.alica.planDesigner.deserialization.RoleTaskDeserializer;
import de.unikassel.vs.alica.planDesigner.serialization.ExternalRefSerializer;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefKeySerializer;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefSerializer;
import java.util.HashMap;

public abstract class RoleMixIn {
    @JsonSerialize(using = InternalRefSerializer.class)
    protected RoleSet roleSet;

    //@JsonSerialize(using = InternalRefSerializer.class)
    @JsonDeserialize(using = RoleDeserializer.class)
    protected HashMap<Task, Float> taskPriorities;

    @JsonSerialize(keyUsing = ExternalRefSerializer.class)
    @JsonDeserialize(keyUsing = RoleTaskDeserializer.class)
    protected Task task;
}
