package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DeletePlan extends Command {

    protected Plan plan;
    protected UiExtension uiExtension;

    private List<File> paths = new ArrayList<File>();
    private List<String> text = new ArrayList<String>();

    public DeletePlan(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        plan = (Plan) manager.getPlanElement(mmq.getElementId());
        uiExtension = modelManager.getUiExtensionMap().get(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if (plan == null) {
            return;
        }
        //Get the generated Files
        String generatedPaths = modelManager.getGeneratedFilesForAbstractPlan(plan);
        generatedPaths = generatedPaths.replace("[","");
        generatedPaths = generatedPaths.replace("]","");
        String[] split = generatedPaths.split(", ");

        for (int i = 0; i < split.length; i++) {
            paths.add(new File(split[i]));
        }

        if(paths.size() != 0) {
            try {
                for (File path: paths) {
                    text.add(new String (Files.readAllBytes(Paths.get(path.toString()))));
                    path.delete();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            System.out.println("ERROR:" + plan.getName() + ".h and " + plan.getName() + ".cpp are not autogenerated!!!");
        }
        modelManager.dropPlanElement(Types.PLAN, plan, true);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.plan);
    }

    @Override
    public void undoCommand() {
        if (plan == null) {
            return;
        }
        //Write back the autogenerated files, if available
        try {
            if(text.size() != 0) {
                for (int i = 0; i < text.size(); i++) {
                    PrintWriter writerH = new PrintWriter(new FileWriter(paths.get(i), true));
                    writerH.print(text.get(i));
                    writerH.close();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: ");
        }

        // The UiExtension has been deleted with the plan, so it needs to be put back into the map
        modelManager.getUiExtensionMap().put(mmq.getElementId(), uiExtension);
        if (!plan.getMasterPlan()) {
            modelManager.storePlanElement(Types.PLAN, plan, true);
        } else {
            modelManager.storePlanElement(Types.MASTERPLAN, plan, true);
        }
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.plan);
    }
}
