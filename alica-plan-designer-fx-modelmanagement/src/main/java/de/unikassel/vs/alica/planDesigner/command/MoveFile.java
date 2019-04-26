package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.alicamodel.SerializablePlanElement;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;

public class MoveFile extends Command {

    private SerializablePlanElement elementToMove;
    private String newAbsoluteDirectory;
    private String originalRelativeDirectory;
    private String ending;
    private String type;

    public MoveFile(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.elementToMove = (SerializablePlanElement) modelManager.getPlanElement(mmq.getElementId());
        this.ending = FileSystemUtil.getExtension(this.elementToMove);
        this.newAbsoluteDirectory = mmq.getAbsoluteDirectory();
        this.originalRelativeDirectory = this.elementToMove.getRelativeDirectory();
        this.type = mmq.getElementType();
    }

    @Override
    public void doCommand() {
        this.modelManager.moveFile(elementToMove, type, newAbsoluteDirectory, ending);
    }

    @Override
    public void undoCommand() {
        this.modelManager.moveFile(elementToMove, type, originalRelativeDirectory, ending);
    }
}
