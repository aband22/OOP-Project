document.addEventListener('DOMContentLoaded', function() {
    const addFillQuestionButton = document.getElementById('add-fill-question-button');
    const addMultipleChoiceQuestionButton = document.getElementById('add-multiple-choice-question-button');
    const addMultipleResponseQuestionButton = document.getElementById('add-multiple-response-question-button');
    const addResponseQuestionButton = document.getElementById('add-response-question-button');
    const questionContainer = document.getElementById('question-container');

    let questionCounter = 1;

    addFillQuestionButton.addEventListener('click', function() {
        addQuestion('fill');
    });

    addMultipleChoiceQuestionButton.addEventListener('click', function() {
        addQuestion('multipleChoice');
    });

    addMultipleResponseQuestionButton.addEventListener('click', function() {
        addQuestion('multipleResponse');
    });

    addResponseQuestionButton.addEventListener('click', function() {
        addQuestion('response');
    });

    function addQuestion(type) {
        const questionPanel = document.createElement('div');
        questionPanel.className = 'question-panel ' + type;
        questionPanel.dataset.questionId = questionCounter;

        const questionHeader = document.createElement('div');
        questionHeader.className = 'question-header';

        const deleteButton = document.createElement('button');
        deleteButton.className = 'delete-button';
        deleteButton.textContent = 'X';
        deleteButton.type = 'button'; // Ensure it's not a submit button
        deleteButton.addEventListener('click', function() {
            questionPanel.remove();
            updateQuestionNumbers();
        });

        questionHeader.appendChild(deleteButton);
        questionPanel.appendChild(questionHeader);

        const questionTypeInput = document.createElement('input');
        questionTypeInput.type = 'hidden';
        questionTypeInput.name = 'questionType' + questionCounter;
        questionTypeInput.value = type;
        questionPanel.appendChild(questionTypeInput);

        const questionLabel = document.createElement('h6');
        questionLabel.className = 'question-label';
        questionLabel.textContent = questionCounter + '. კითხვა:';
        questionPanel.appendChild(questionLabel);

        const questionInput = document.createElement('textarea');
        questionInput.classList.add('form-control');
        questionInput.rows = 1;
        questionInput.name = 'questionText' + questionCounter;
        questionPanel.appendChild(questionInput);
        if(type!=="fill") {
            const imageLabel = document.createElement('label');
            imageLabel.textContent = 'Image:';
            questionPanel.appendChild(imageLabel);

            const imageInput = document.createElement('input');
            imageInput.type = 'file';
            imageInput.accept = 'image/*';
            imageInput.name = 'questionImage' + questionCounter;
            questionPanel.appendChild(imageInput);
        }


        const answerLabel = document.createElement('h6');
        answerLabel.className = 'question-label';
        answerLabel.textContent = 'პასუხი:';
        questionPanel.appendChild(answerLabel);

        const answerContainer = document.createElement('div');
        answerContainer.className = 'answer-container';
        questionPanel.appendChild(answerContainer);

        const quest = questionCounter;
        if (type !== 'response') {
            addResponseField(type, answerContainer, questionCounter);
            const addAnswerButton = document.createElement('button');
            addAnswerButton.type = 'button';
            addAnswerButton.className = 'btn btn-warning';
            addAnswerButton.textContent = 'Add Answer';
            addAnswerButton.addEventListener('click', function() {
                addResponseField(type, answerContainer, quest);
            });
            questionPanel.appendChild(addAnswerButton);
        } else {
            const answerInput = document.createElement('input');
            answerInput.type = 'text';
            answerInput.classList.add('form-control');
            answerInput.placeholder = 'პასუხი';
            answerInput.name = 'answer' + questionCounter + '_1';
            questionPanel.appendChild(answerInput);
        }

        questionCounter++;
        questionContainer.appendChild(questionPanel);
    }

    function addResponseField(type, container, questionCounter) {
        const answerGroups = container.querySelectorAll('.form-group');
        const answerCounter = answerGroups.length + 1;

        const formGroup = document.createElement('div');
        formGroup.className = 'form-group d-flex align-items-center';

        const answerInput = document.createElement('input');
        answerInput.type = 'text';
        answerInput.classList.add('form-control');
        answerInput.placeholder = 'პასუხი';
        answerInput.name = `answer${questionCounter}_${answerCounter}`;


        if (type === 'multipleChoice') {

            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.className = 'form-check-input me-2';
            checkbox.name = `choice${questionCounter}_${answerCounter}`;
            formGroup.appendChild(checkbox);

            const checkboxLabel = document.createElement('label');
            checkboxLabel.className = 'form-check-label';
            formGroup.appendChild(checkboxLabel);

        }
        formGroup.appendChild(answerInput);
        if (type === 'multipleChoice') {
            const imageLabel1 = document.createElement('label');
            imageLabel1.textContent = 'Image:';
            formGroup.appendChild(imageLabel1);

            const imageInput1 = document.createElement('input');
            imageInput1.type = 'file';
            imageInput1.accept = 'image/*';
            imageInput1.name = `answerimage${questionCounter}_${answerCounter}`;
            formGroup.appendChild(imageInput1);
        }


        container.appendChild(formGroup);
    }

    function updateQuestionNumbers() {
        const questionPanels = document.querySelectorAll('.question-panel');
        questionCounter = 1;
        questionPanels.forEach(panel => {
            const questionLabel = panel.querySelector('.question-label');
            questionLabel.textContent = questionCounter + '. კითხვა:';
            questionCounter++;
        });
    }
});
const dropArea = document.getElementById("drop-area");
const inputFile = document.getElementById("input-file");
const imageView = document.getElementById("img-view");

inputFile.addEventListener("change", uploadImage);

function uploadImage(){
    let imgLink = URL.createObjectURL(inputFile.files[0]);
    imageView.style.backgroundImage = `url(${imgLink})`;
    imageView.textContent = "";
    imageView.style.border = 0;
}

dropArea.addEventListener("dragover", function(e){
    e.preventDefault();
});

dropArea.addEventListener("drop", function(e){
    e.preventDefault();
    inputFile.files = e.dataTransfer.files;
    uploadImage();
});

