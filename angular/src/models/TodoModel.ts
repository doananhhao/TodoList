export class TodoModel {
	completed: boolean;
	title: string;
	id: number;

	constructor(title) {
		this.completed = false;
		this.title = title.trim();
	}
}