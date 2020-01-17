import { JobSkillRel } from './jobskillrel.model';
import { JobQuestionsRel } from './jobquestions.model';
import { JobInterviewRel } from './jobinterview.model';

export class AddJobOpening {
    public title: String;
    public jobStatus: String;
    public hiringLead: String;
    public department: String;
    public employeeType: String;
    public jobDescription: String;
    public minimumExp: number;
    public location : String;
    public compensation : String;
    public jobSkills : JobSkillRel[];
    public jobQuestionsRels : JobQuestionsRel[];
    public jobInterviewRels : JobInterviewRel[];
    public noofopening: number;

}
