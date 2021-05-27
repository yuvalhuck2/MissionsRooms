const checkIfSolvedTriviaMission = (mission, currentAnswers) => {
    let triviaQuestions = mission.triviaQuestionMap.values();
    let correctAnswers = triviaQuestions.map ( q => q.correctAnswer);
    let amountOfQuestions = correctAnswers.length;
    let actualRatio = calculateNumberOfRightAnswers(correctAnswers, currentAnswers) / amountOfQuestions;

    return actualRatio >= mission.passRatio
}