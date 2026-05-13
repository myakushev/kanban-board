export class Task {

    id: number;
    title: String;
    description: String;
    color: String;
    status: String;
    systemTask?: boolean;
    externalId?: string;

    constructor() {
        this.systemTask = false;
    }
}
