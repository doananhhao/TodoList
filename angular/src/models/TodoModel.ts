export class TodoModel {
	completed: boolean;
	title: string;
	id: number;
	order: number;

	constructor(title) {
		this.completed = false;
		this.title = title.trim();
	}
}