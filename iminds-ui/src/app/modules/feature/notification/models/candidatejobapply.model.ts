import { CandidateJobRel } from './candidatejobrel.model';
import { CandidateSkillRel } from './candidatekillrel.model';

export class CandidateJobApply {
    public name: String;
    public email: String;
    public mobile: String;
    public candidateSkills : CandidateSkillRel[];
    public candidateJobRel : CandidateJobRel[];

}
