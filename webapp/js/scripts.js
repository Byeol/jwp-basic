String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

(function() {
  "use strict";

  const createAnswerUrl = '/api/qna/createAnswer';
  const answerTemplate = $('#answerTemplate').html();
  const $answerForm = $('form[name="answer"]');
  const $answers = $('.qna-comment-slipp-articles');
  const $answerCount = $('.qna-comment-count strong');

  $answerForm.submit(event => {
    event.preventDefault();

    let $form = $(event.target);
    $.post(createAnswerUrl, $form.serialize())
        .done(appendAnswer)
        .done(() => $form[0].reset())
        .fail(displayError);
  });

  $answers.on('click', 'article .form-delete', event => {
    event.preventDefault();

    if (!window.confirm('Are you sure?')) {
      return;
    }

    let $form = $(event.target).closest('form');
    $.post($form.attr('action'), $form.serialize())
        .done(() => removeAnswer($form.closest('article')))
        .fail(displayError);
  });

  function displayError(xhr) {
    window.alert(xhr.responseJSON.message);
  }

  function appendAnswer(answer) {
    let html = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId)
    $answers.prepend(html);
    updateCount();
  }

  function removeAnswer($answer) {
    $answer.remove();
    updateCount();
  }

  function updateCount() {
    $answerCount.text($answers.find('article').length);
  }
})();
